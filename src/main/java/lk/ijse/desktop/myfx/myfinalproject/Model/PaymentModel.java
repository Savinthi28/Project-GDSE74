package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.PaymentDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    public static boolean updatePayment(PaymentDto paymentDto) throws SQLException {
        return CrudUtil.execute(
                "update Payment set Order_ID = ?, Customer_ID = ?, Payment_Date = ?, Payment_Method = ?, Payment_Amount = ? where Payment_ID = ?",
                paymentDto.getOrderId(),
                paymentDto.getCustomerId(),
                paymentDto.getDate(),
                paymentDto.getPaymentMethod(),
                paymentDto.getAmount(),
                paymentDto.getPaymentId()
        );
    }

    public static ArrayList<Integer> getAllCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT DISTINCT Customer_ID FROM Customer");
        ArrayList<Integer> customerIds = new ArrayList<>();
        while (rst.next()) {
            Integer customerId = rst.getInt(1);
            customerIds.add(customerId);
        }
        return customerIds;
    }

    public static ArrayList<Integer> getAllOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT DISTINCT Order_ID FROM Payment");
        ArrayList<Integer> orderIds = new ArrayList<>();
        while (rst.next()) {
            Integer orderId = rst.getInt(1);
            orderIds.add(orderId);
        }
        return orderIds;
    }

    public static ArrayList<String> getAllPaymentMethod() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT DISTINCT Payment_Method FROM Payment");
        ArrayList<String> paymentMethods = new ArrayList<>();
        while (rst.next()) {
            String paymentMethod = rst.getString(1);
            paymentMethods.add(paymentMethod);
        }
        return paymentMethods;
    }

    public String getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Payment_ID from Payment order by Payment_ID desc limit 1");
        if (resultSet.next()) {
            int lastId = resultSet.getInt(1);
            int nextId = lastId + 1;
            return String.valueOf(nextId);
        }
        return "1";
    }

    public ArrayList<PaymentDto> viewAllPayment() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Payment");
        ArrayList<PaymentDto> payments = new ArrayList<>();
        while (rs.next()) {
            PaymentDto paymentDto = new PaymentDto(
                    rs.getInt("Payment_ID"),
                    rs.getInt("Order_ID"),
                    rs.getInt("Customer_ID"),
                    rs.getString("Payment_Date"),
                    rs.getString("Payment_Method"),
                    rs.getDouble("Payment_Amount")
            );
            payments.add(paymentDto);
        }
        return payments;
    }
    public boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Payment values (?,?,?,?,?,?)",
                paymentDto.getPaymentId(),
                paymentDto.getOrderId(),
                paymentDto.getCustomerId(),
                paymentDto.getDate(),
                paymentDto.getPaymentMethod(),
                paymentDto.getAmount()
        );
    }

    public boolean deletePayment(PaymentDto paymentDto) throws SQLException{
        String sql = "delete from Payment where Payment_ID=?";
        return CrudUtil.execute(sql, paymentDto.getPaymentId());
    }
}
