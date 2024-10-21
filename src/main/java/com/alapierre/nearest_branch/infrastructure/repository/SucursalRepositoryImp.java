package com.alapierre.nearest_branch.infrastructure.repository;

import com.alapierre.nearest_branch.domain.model.Sucursal;
import com.alapierre.nearest_branch.domain.repository.SucursalRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SucursalRepositoryImp extends MongoRepository<Sucursal, String>, SucursalRepository {

}
