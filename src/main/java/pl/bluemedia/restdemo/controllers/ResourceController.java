package pl.bluemedia.restdemo.controllers;


import pl.bluemedia.restdemo.models.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @RequestMapping(method=RequestMethod.GET, value="/resources")
    public Iterable<Resource> resource() {
        return resourceRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, value="/resources")
    public String save(@RequestBody Resource resource) {
        resourceRepository.save(resource);

        return resource.getId();
    }

    @RequestMapping(method=RequestMethod.GET, value="/resources/{id}")
    public Resource show(@PathVariable String id) {
        return resourceRepository.findById(id).get();
    }

    @RequestMapping(method=RequestMethod.PUT, value="/resources/{id}")
    public Resource update(@PathVariable String id, @RequestBody Resource resource) {
        Resource res = resourceRepository.findById(id).get();
        
        if(resource.getResName()!= null)
            res.setResName(resource.getResName());
        if(resource.getResPath()!= null)
            res.setResPath(resource.getResPath());
        
        resourceRepository.save(res);
        return res;
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/resources/{id}")
    public String delete(@PathVariable String id) {
        Resource resource = resourceRepository.findById(id).get();
        resourceRepository.delete(resource);

        return "resource deleted";
    }
}
