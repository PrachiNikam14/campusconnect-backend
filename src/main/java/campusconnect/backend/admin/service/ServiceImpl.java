package campusconnect.backend.admin.service;

import campusconnect.backend.entity.ServiceType;
import campusconnect.backend.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl {

    @Autowired
    private ServiceRepository serviceRepo;

    public List<ServiceType> getAllServices(){
        return serviceRepo.findAll();
    }

    public ServiceType getServiceById(Long id){
        return serviceRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Service not found"));
    }

    public String deleteServiceById(Long id){
         serviceRepo.deleteById(id);
         return "Service deleted";
    }

}
