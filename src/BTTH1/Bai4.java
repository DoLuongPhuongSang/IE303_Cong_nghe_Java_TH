/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BTTH1;

/**
 *
 * @author pc
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bai4{
    private Map<String, Integer> tanSoTu;
    private Map<String, Map<String, Integer>> tanSoCapTu;
    private Map<String, Double> xacSuatTu;
    private Map<String, Map<String, Double>> xacSuatCoDieuKien;
    private int tongSoTu;

    public Bai4() {
        tanSoTu = new HashMap<>();
        tanSoCapTu = new HashMap<>();
        xacSuatTu = new HashMap<>();
        xacSuatCoDieuKien = new HashMap<>();
        tongSoTu = 0;
    }

    public void loadData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(" ");
            }
            hocMoHinh(content.toString().trim());
        } catch (IOException e) {
            System.err.println("Loi khi doc file: " + e.getMessage());
        }
    }

    public void hocMoHinh(String vanBan) {
        String[] tu = vanBan.split("\\s+");
        tongSoTu = tu.length;

        for (int i = 0; i < tu.length; i++) {
            String tuHienTai = tu[i];
            tanSoTu.put(tuHienTai, tanSoTu.getOrDefault(tuHienTai, 0) + 1);
            
            if (i < tu.length - 1) {
                String tuTiepTheo = tu[i + 1];
                tanSoCapTu.putIfAbsent(tuHienTai, new HashMap<>());
                tanSoCapTu.get(tuHienTai).put(tuTiepTheo, tanSoCapTu.get(tuHienTai).getOrDefault(tuTiepTheo, 0) + 1);
            }
        }

        for (String tuHienTai : tanSoTu.keySet()) {
            xacSuatTu.put(tuHienTai, (double) tanSoTu.get(tuHienTai) / tongSoTu);
        }

        for (String tuHienTai : tanSoCapTu.keySet()) {
            Map<String, Integer> tuTiepTheoMap = tanSoCapTu.get(tuHienTai);
            int tongTanSo = tuTiepTheoMap.values().stream().mapToInt(Integer::intValue).sum();
            
            Map<String, Double> xacSuatMap = new HashMap<>();
            for (String tuTiepTheo : tuTiepTheoMap.keySet()) {
                xacSuatMap.put(tuTiepTheo, (double) tuTiepTheoMap.get(tuTiepTheo) / tongTanSo);
            }
            xacSuatCoDieuKien.put(tuHienTai, xacSuatMap);
        }
    }

    public String sinhVanBan(String tuDauTien) {
        StringBuilder vanBanTaoRa = new StringBuilder(tuDauTien);
        String tuHienTai = tuDauTien;

        for (int i = 0; i < 5; i++) {
            if (!xacSuatCoDieuKien.containsKey(tuHienTai)) break;
            
            Map<String, Double> xacSuatTuTiep = xacSuatCoDieuKien.get(tuHienTai);
            tuHienTai = chonTuTiepTheo(xacSuatTuTiep);
            vanBanTaoRa.append(" ").append(tuHienTai);
        }
        return vanBanTaoRa.toString();
    }

    private String chonTuTiepTheo(Map<String, Double> xacSuat) {
        double rand = Math.random();
        double xacSuatTichLuy = 0.0;

        for (Map.Entry<String, Double> muc : xacSuat.entrySet()) {
            xacSuatTichLuy += muc.getValue();
            if (rand <= xacSuatTichLuy) {
                return muc.getKey();
            }
        }
        return xacSuat.keySet().iterator().next();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bai4 sinhVanBan = new Bai4();
        
        String datasetFile = "./src/BTTH1/UIT-ViOCD.txt";
        sinhVanBan.loadData(datasetFile);
        
        System.out.print("Nhap tu dau tien: ");
        String tuDauTien = scanner.next();
        
        System.out.println("Van ban tao ra: " + sinhVanBan.sinhVanBan(tuDauTien));
    }
}
