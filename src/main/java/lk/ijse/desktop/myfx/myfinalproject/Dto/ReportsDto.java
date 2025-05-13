package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ReportsDto {
    private int reportId;
    private String date;
    private int userId;
    private String reportType;
    private String generateBy;

    public ReportsDto(int reportId) {
        this.reportId = reportId;
    }
}
