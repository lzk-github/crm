package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Account;
import com.kaishengit.entity.Customer;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface CustomerService {

    List<Customer> getCustomers(Account account);

    List<String> getCustomerSources();

    List<String> getCustomerTrades();

    void saveCustomer(Customer customer, Account account);

    PageInfo<Customer> findCustomersByQueryMap(Map<String, Object> queryItemsMap);

    Customer findById(Integer id);

    void updateCustomer(Customer customer);

    void delById(Integer id);

    void pullToSea(Customer customer,Account account);

    List<Account> findAllAccount();

    void exportCustomersToExcel(Account account, OutputStream outputStream);

    PageInfo<Customer> findPublicSeaCustomersByQueryMap(Map<String, Object> map);

    void savePublicSeaCustomer(Customer customer);

    void transformToMyCustomer(Account account, Integer id);

    List<Customer> getAllCustomers();

    List<Map<String,Object>> analyzeLevelData();
}
