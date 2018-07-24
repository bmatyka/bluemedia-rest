package pl.bluemedia.restdemo.repository;


import pl.bluemedia.restdemo.models.Resource;
import org.springframework.data.repository.CrudRepository;


public interface ResourceRepository extends CrudRepository<Resource, String> {
}
