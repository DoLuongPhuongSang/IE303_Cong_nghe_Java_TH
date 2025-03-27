/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BTTH1;

/**
 *
 * @author pc
 */
import java.util.*;
public class Bai3 {
    static class Diem {
        int x, y;

        Diem(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // So sánh 2 điểm
    static int huong(Diem p, Diem q, Diem r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0; // collinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // Tìm các trạm cảnh báo
    static List<Diem> timTramCanhBao(Diem[] diem) {
        int n = diem.length;
        if (n < 3) return Arrays.asList(diem); // Khong du diem de tao thanh hinh

        List<Diem> hull = new ArrayList<>();

        // Tim diem ben trai nhat
        int leftMost = 0;
        for (int i = 1; i < n; i++)
            if (diem[i].x < diem[leftMost].x)
                leftMost = i;

        int p = leftMost, q;
        do {
            hull.add(diem[p]);
            q = (p + 1) % n;
            for (int i = 0; i < n; i++) {
                if (huong(diem[p], diem[i], diem[q]) == 2) {
                    q = i;
                }
            }
            p = q;
        } while (p != leftMost && hull.size() < n);

        return hull;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhap so luong tram: ");
        int n = scanner.nextInt(); // Số lượng trạm
        Diem[] diem = new Diem[n];

        // Nhập tọa độ các trạm
        System.out.println("Nhap toa do cac tram: ");
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            diem[i] = new Diem(x, y);
        }

        // Tìm các trạm cảnh báo
        List<Diem> tramCanhBao = timTramCanhBao(diem);

        // In ra tọa độ của các trạm cảnh báo
        System.out.println("Toa do cac tram duoc su dung lam tram canh bao:");
        for (Diem p : tramCanhBao) {
            System.out.println(p.x + " " + p.y);
        }

        scanner.close();
    }
}
