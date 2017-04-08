package com.garbage.disposal.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garbage.disposal.model.Location;

public interface LocationDAO extends JpaRepository<Location, Integer> {

}
