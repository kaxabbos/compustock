package com.compustock.service;

import com.compustock.model.StatProduct;
import com.compustock.model.enums.ProductStatus;
import com.compustock.repo.StatProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatProductService {

    private final StatProductRepo statProductRepo;

    @Autowired
    public StatProductService(StatProductRepo statProductRepo) {
        this.statProductRepo = statProductRepo;
    }

    public void add(StatProduct statProduct) {
        statProductRepo.save(statProduct);
    }

    public void update(StatProduct statProduct) {
        statProductRepo.save(statProduct);
    }

    public void delete(Long id) {
        statProductRepo.deleteById(id);
    }

    public void delete(StatProduct statProduct) {
        statProductRepo.delete(statProduct);
    }

    public StatProduct find(Long id) {
        return statProductRepo.findStatProductById(id);
    }

    public StatProduct findByIdOrderingDetail(Long id) {
        return statProductRepo.findByIdOrderingDetail(id);
    }

    public List<StatProduct> findAllByProductStatusAndDateOrderByIdDesc(ProductStatus productStatus, String date) {
        return statProductRepo.findAllByProductStatusAndDateOrderByIdDesc(productStatus, date);
    }

    public List<StatProduct> findAllByOrderByIdDesc() {
        return statProductRepo.findAllByOrderByIdDesc();
    }

    public List<StatProduct> findAllByProductStatus(ProductStatus productStatus) {
        return statProductRepo.findAllByProductStatus(productStatus);
    }

    public List<StatProduct> findAllByDateOrderByIdDesc(String date) {
        return statProductRepo.findAllByDateOrderByIdDesc(date);
    }

    public List<StatProduct> findAllByProductStatusOrderByIdDesc(ProductStatus productStatus) {
        return statProductRepo.findAllByProductStatusOrderByIdDesc(productStatus);
    }

    public StatProduct addAndFlush(StatProduct statProduct) {
        return statProductRepo.saveAndFlush(statProduct);
    }
}
