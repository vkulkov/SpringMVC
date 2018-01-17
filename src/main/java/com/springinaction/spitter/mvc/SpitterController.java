package com.springinaction.spitter.mvc;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.service.SpitterService;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.Valid;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/spitter")
public class SpitterController {
    private SpitterService spitterService;
    @Value("#{s3Properties['s3.accessKey']}")
    private String s3AccessKey;
    @Value("#{s3Properties['s3.secretKey']}")
    private String s3SecretKey;

    @Inject
    public SpitterController(SpitterService spitterService) {
        this.spitterService = spitterService;
    }

    @RequestMapping(value = "/spittles", method = GET)
    public String listSpittlesForSpitter(@RequestParam("spitter") String username, Model model) {
        Spitter spitter = spitterService.getSpitter(username);
        model.addAttribute(spitter);
        model.addAttribute(spitterService.getSpittlesForSpitter(username));
        return "spittles/list";
    }

    @RequestMapping(method = GET, params = "new")
    public String createSpitterProfile(Model model) {
        model.addAttribute(new Spitter());
        return "spitters/edit";
    }

    @RequestMapping(method = POST)
    public String addSpitterFromForm(@Valid Spitter spitter, BindingResult bindingResult,
                                     @RequestParam(value = "image", required = false) MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return "spitters/edit";
        }

        spitterService.saveSpitter(spitter);

        try {
            if (!image.isEmpty()) {
                validateImage(image);

                saveImage(spitter.getId() + ".jpg", image);
            }
        } catch (ImageUploadException e) {
            bindingResult.reject(e.getMessage());
            return "spitters/edit";
        }
        return "redirect:/spitters/" + spitter.getUsername();
    }

    @RequestMapping(value = "/{username}", method = GET)
    public String showSpitterProfile(@PathVariable String username, Model model) {
        model.addAttribute(spitterService.getSpitter(username));
        return "spitters/view";
    }

    private void validateImage(MultipartFile image) {
        if (!image.getContentType().equals("image/jpeg")) {
            throw new ImageUploadException("Only JPG images accepted");
        }
    }

    private void saveImage(String filename, MultipartFile image) throws ImageUploadException {
        try {
            AWSCredentials awsCredentials = new AWSCredentials(s3AccessKey, s3SecretKey);
            S3Service s3 = new RestS3Service(awsCredentials);

            S3Bucket imageBucket = s3.createBucket("spitterImages");
            S3Object imageObject = new S3Object(filename);

            imageObject.setDataInputStream(new ByteArrayInputStream(image.getBytes()));
            imageObject.setContentLength(image.getBytes().length);
            imageObject.setContentType("image/jpeg");

            AccessControlList acl = new AccessControlList();
            acl.setOwner(imageBucket.getOwner());
            acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
            imageObject.setAcl(acl);

            s3.putObject(imageBucket, imageObject);
        } catch (S3ServiceException | IOException e) {
            throw new ImageUploadException("Unable to save image", e);
        }
    }
}
