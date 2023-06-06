package com.compustock.repo;

import com.compustock.model.OrderingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderingDetailRepo extends JpaRepository<OrderingDetail, Long> {
    OrderingDetail findOrderingDetailById(Long id);
}
