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
