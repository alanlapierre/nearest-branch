package com.alapierre.nearby_store.infrastructure.repository;

import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.domain.repository.SucursalRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SucursalRepositoryImp extends MongoRepository<Sucursal, String>, SucursalRepository {

}
