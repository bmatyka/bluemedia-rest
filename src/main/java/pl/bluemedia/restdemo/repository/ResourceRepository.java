package pl.bluemedia.restdemo.repository;


import pl.bluemedia.restdemo.models.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, String> {
}
