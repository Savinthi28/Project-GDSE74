package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.CustomerDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public ArrayList<CustomerDto> viewAllCustomer() throws SQLException {
        ResultSet ts = CrudUtil.execute("SELECT * FROM Customer");
        ArrayList<CustomerDto> customers = new ArrayList<>();
        while (ts.next()) {
            CustomerDto customerDto = new CustomerDto(
                    ts.getInt("Customer_ID"),
                    ts.getString("Customer_Name"),
                    ts.getString("Address"),
                    ts.getString("Customer_Number")
            );
            customers.add(customerDto);
        }
        return customers;
    }
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Customer values (?,?,?,?)",
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getAddress(),
                customerDto.getCustomerNumber()
        );
    }

    public boolean deleteCustomer(CustomerDto customerDto) throws SQLException{
        String sql = "delete from Customer where Customer_ID=?";
        return CrudUtil.execute(sql, customerDto.getCustomerId());
    }
}
