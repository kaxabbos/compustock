package com.compustock.service;

import com.compustock.model.Client;
import com.compustock.model.Ordering;
import com.compustock.model.enums.OrderingStatus;
import com.compustock.repo.OrderingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderingService {

    private final OrderingRepo orderingRepo;

    @Autowired
    public OrderingService(OrderingRepo orderingRepo) {
        this.orderingRepo = orderingRepo;
    }

    public void add(Ordering ordering) {
        orderingRepo.save(ordering);
    }

    public void update(Ordering ordering) {
        orderingRepo.save(ordering);
    }

    public void delete(Long id) {
        orderingRepo.deleteById(id);
    }

    public void delete(Ordering ordering) {
        orderingRepo.delete(ordering);
    }

    public Ordering find(Long id) {
        return orderingRepo.findOrderingById(id);
    }

    public List<Ordering> findAll() {
        return orderingRepo.findAll();
    }

    public List<Ordering> findAllByOrderByIdDesc() {
        return orderingRepo.findAllByOrderByIdDesc();
    }

    public List<Ordering> findAllByOrderingStatus(OrderingStatus orderingStatus) {
        return orderingRepo.findAllByOrderingStatus(orderingStatus);
    }

    public List<Ordering> findAllByOrderingStatusOrderByIdDesc(OrderingStatus orderingStatus) {
        return orderingRepo.findAllByOrderingStatusOrderByIdDesc(orderingStatus);
    }

    public List<Ordering> findAllByDateOrderByIdDesc(String date) {
        return orderingRepo.findAllByDateOrderByIdDesc(date);
    }

    public List<Ordering> findAllByOrderingStatusAndDateOrderByIdDesc(OrderingStatus orderingStatus, String date) {
        return orderingRepo.findAllByOrderingStatusAndDateOrderByIdDesc(orderingStatus, date);
    }

    public int countByOrderingStatus(OrderingStatus orderingStatus) {
        return orderingRepo.countByOrderingStatus(orderingStatus);
    }

    public Ordering findByClientAndDateAndOrderingStatus(Client client, String date, OrderingStatus orderingStatus) {
        return orderingRepo.findByClientAndDateAndOrderingStatus(client, date, orderingStatus);
    }

    public Ordering addAndFlush(Ordering ordering) {
        return orderingRepo.saveAndFlush(ordering);
    }
}
