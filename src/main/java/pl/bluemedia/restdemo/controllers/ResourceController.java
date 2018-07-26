package pl.bluemedia.restdemo.controllers;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudsearchdomain.model.ContentType;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.InputStream;
import pl.bluemedia.restdemo.models.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.bluemedia.restdemo.repository.ResourceRepository;

@RestController
public class ResourceController {

    @Autowired
    ResourceRepository resourceRepository;

    @Value("${s3Location}")
    private String s3Location;

    @Value("${s3Bucket}")
    private String s3Bucket;

    @Value("${s3Region}")
    private String s3Region;

    AWSCredentials credentials = new BasicAWSCredentials("AKIAIUC7SFYAJMCRJSMA",
            "w49lR4hiBsvMYMn8UGRJne8c2Wf4/Qy9UwpwPAOo");

    AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.valueOf(s3Region)).build();

    @RequestMapping(method = RequestMethod.GET, value = "/resources")
    public Iterable<Resource> resource() {

        return resourceRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resources")
    public String save(@RequestBody InputStream uploadedInputStream, ContentType contentType, String fileName) {

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("APPLICATION/OCTET-STREAM");
            s3client.putObject("bluemedia-rest", fileName, uploadedInputStream, objectMetadata);
        } catch (SdkClientException ex) {
            throw new AWSs3ConflictException(ex.getMessage());
        }

        resourceRepository.save(resource(fileName, s3Location + s3Bucket + "/" + fileName));
        return resource.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resources/{id}")
    public Resource show(@PathVariable String id) {
        return resourceRepository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/resources/{id}")
    public Resource update(@PathVariable String id, @RequestBody Resource resource) {
        Resource res = resourceRepository.findById(id).get();

        if (resource.getResName() != null) {
            res.setResName(resource.getResName());
        }
        if (resource.getResPath() != null) {
            res.setResPath(resource.getResPath());
        }

        resourceRepository.save(res);
        return res;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/resources/{id}")
    public String delete(@PathVariable String id) {
        Resource resource = resourceRepository.findById(id).get();
        resourceRepository.delete(resource);

        return "resource deleted";
    }
}
