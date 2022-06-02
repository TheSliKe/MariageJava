package com.valarep.mariage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InviteRepository extends CrudRepository<InviteDB, String> {

    Optional<InviteDB> findById(UUID uuid);
}
