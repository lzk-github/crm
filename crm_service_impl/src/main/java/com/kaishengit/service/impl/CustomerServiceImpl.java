package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Account;
import com.kaishengit.entity.AccountExample;
import com.kaishengit.entity.Customer;
import com.kaishengit.entity.CustomerExample;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.AccountMapper;
import com.kaishengit.mapper.CustomerMapper;
import com.kaishengit.service.CustomerService;
import com.kaishengit.weixin.util.WeixinUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private WeixinUtil weixinUtil;
    /**
     * 自动注入CustomerMapper的实现类,使用Spring EL技术
     */
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 自动注入客户来源列表,使用Spring EL技术
     */
    @Value("#{'${customer.resource}'.split(',')}")
    private List<String> customerResource;

    /**
     * 自动注入客户行业列表
     *
     */
    @Value("#{'${customer.trade}'.split(',')}")
    private List<String> customerTrade;

    /**
     * 自动注入AccountMapper的实现类
     */
    @Autowired
    private AccountMapper accountMapper;


    /**
     * 获取对应员工所属的客户列表
     *
     * @param account 员工信息
     * @return 该员工的客户列表
     */
    @Override
    public List<Customer> getCustomers(Account account) {
        //如果没有Account对象传过来,返回公海客户列表
        if(account == null) {
            return customerMapper.selectByAccountIdIsNull();
        }

        Integer accountId = account.getId();

        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(accountId);
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        return customerList;
    }

    /**
     * @return  返回客户来源列表
     */
    @Override
    public List<String> getCustomerSources() {
        return customerResource;
    }

    /**
     * @return 返回客户行业列表
     */
    @Override
    public List<String> getCustomerTrades() {
        return customerTrade;
    }

    /**
     * 保存新客户,并在企业微信中通知管理员
     * @param customer 客户对象
     * @param account 当前登录员工对象
     */
    @Override
    @Transactional
    public void saveCustomer(Customer customer, Account account) {
        Integer accountId = account.getId();
        customer.setAccountId(accountId);
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customer.setLastContactTime(new Date());
        //新增客户
        customerMapper.insert(customer);
        //通知管理员
        weixinUtil.sendMessageToUser("新增客户:" + customer.getCustName(),"LuZhiKuan");

    }

    /**
     * 根据传入的map集合中的查询条件返回PageInfo对象
     * @param map 查询条件组成的map集合
     * @return PageInfo对象,用于前端分页
     */
    @Override
    public PageInfo<Customer> findCustomersByQueryMap(Map<String, Object> map) {
        Integer pageNo = (Integer) map.get("pageNo");
        PageHelper.startPage(pageNo,5);

        /*String keyword = (String) map.get("keyword");
        Integer accountId = (Integer) map.get("accountId");*/

        List<Customer> customerList = customerMapper.findByMap(map);

        return new PageInfo<Customer>(customerList);
    }

    /**
     * 根据ID查找客户
     * @param id 客户id
     * @return 客户对象
     */
    @Override
    public Customer findById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新客户信息
     * @param customer
     */
    @Override
    public void updateCustomer(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 根据客户id删除客户
     * @param id
     */
    @Override
    public void delById(Integer id) {
        customerMapper.deleteByPrimaryKey(id);
    }

    /**
     * 将客户放入公海
     * @param customer 客户对象
     */
    @Override
    public void pullToSea(Customer customer,Account account) {
        customer.setAccountId(null);
        customer.setUpdateTime(new Date());
        customer.setRemainder(account.getUserName() + "放入公海的客户");
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 查找所有Account对象
     * @return Account集合
     */
    @Override
    public List<Account> findAllAccount() {
        return  accountMapper.selectByExample(new AccountExample());
    }

    /**
     * 将客户信息导出至excel
     * @param account 哪个员工的客户信息
     * @param outputStream 响应输出流
     */
    @Override
    public void exportCustomersToExcel(Account account, OutputStream outputStream) {
        //获取客户集合
        List<Customer> customerList = getCustomers(account);

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("客户信息sheet");
        Row row = sheet.createRow(0);

        //excel表的第一行表头
        row.createCell(0).setCellValue("客户姓名");
        row.createCell(1).setCellValue("客户职位");
        row.createCell(2).setCellValue("客户手机号");
        row.createCell(3).setCellValue("客户地址");
        row.createCell(4).setCellValue("客户行业");
        row.createCell(5).setCellValue("客户来源");
        row.createCell(6).setCellValue("客户等级");
        row.createCell(7).setCellValue("客户备注");
//        row.createCell(8).setCellValue("最后一次联系时间");
        row.createCell(9).setCellValue("客户信息创建时间");
        row.createCell(10).setCellValue("客户性别");
//        row.createCell(11).setCellValue("客户信息最后一次更新时间");
        row.createCell(12).setCellValue("remainder");

        //excel表中的数据
        for (int i = 1; i <= customerList.size(); i++) {
            Row rowInfo = sheet.createRow(i);
            rowInfo.createCell(0).setCellValue(customerList.get(i - 1).getCustName());
            rowInfo.createCell(1).setCellValue(customerList.get(i - 1).getJobTitle());
            rowInfo.createCell(2).setCellValue(customerList.get(i - 1).getMobile());
            rowInfo.createCell(3).setCellValue(customerList.get(i - 1).getAddress());
            rowInfo.createCell(4).setCellValue(customerList.get(i - 1).getTrade());
            rowInfo.createCell(5).setCellValue(customerList.get(i - 1).getSource());
            rowInfo.createCell(6).setCellValue(customerList.get(i - 1).getLevel());
            rowInfo.createCell(7).setCellValue(customerList.get(i - 1).getMark());
//            rowInfo.createCell(8).setCellValue(customerList.get(i - 1).getLastContactTime());
            rowInfo.createCell(9).setCellValue(customerList.get(i - 1).getCreateTime());
            rowInfo.createCell(10).setCellValue(customerList.get(i - 1).getSex());
//            rowInfo.createCell(11).setCellValue(customerList.get(i - 1).getUpdateTime());
            rowInfo.createCell(12).setCellValue(customerList.get(i - 1).getRemainder());
        }

        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new ServiceException("导出excel失败");
        }

    }

    /**
     * 根据条件查询公海客户
     * @param map 查询条件
     * @return 保护了公海客户集合的PageInfo对象
     */
    @Override
    public PageInfo<Customer> findPublicSeaCustomersByQueryMap(Map<String, Object> map) {
        PageHelper.startPage((Integer) map.get("pageNo"),5);

        List<Customer> customerList = customerMapper.findPublicSeaByParam(map);

        return new PageInfo<>(customerList);
    }

    /**
     * 新增公海客户
     * @param customer 客户对象
     */
    @Override
    public void savePublicSeaCustomer(Customer customer) {
        //设置公海客户信息的创建时间
        customer.setCreateTime(new Date());
        customerMapper.insert(customer);
    }

    /**
     * 将公海客户变为我的客户
     * @param account 员工信息
     * @param id 公海客户id
     */
    @Override
    public void transformToMyCustomer(Account account, Integer id) {
        Customer customer = customerMapper.selectByPrimaryKey(id);
        customer.setAccountId(account.getId());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 获取所有客户的列表
     * @return
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerMapper.selectByExample(new CustomerExample());
    }

    /**
     * 分析用户星级/等级数据
     * @return
     */
    @Override
    public List<Map<String, Object>> analyzeLevelData() {
        return customerMapper.analyzeLevel();
    }

}
