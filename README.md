# 📚 Hệ thống Quản lý Thư viện (Library SOA Demo)

Dự án Demo áp dụng kiến trúc **Service-Oriented Architecture (SOA)** và **Microservices** với Spring Boot. Hệ thống mô phỏng nghiệp vụ thư viện bao gồm: Quản lý sách, Độc giả, và Mượn trả sách.

---

## ⚠️ Lưu ý quan trọng về Kiến trúc (Architecture Note)

**Về Service Discovery (Eureka Server):**
Trong phiên bản này, chúng em đã **loại bỏ Discovery Server (Eureka)** để tối ưu hóa việc triển khai Demo và giảm thiểu tài nguyên, công việc vì có thành viên không tham gia được. Thay vào đó, hệ thống sử dụng cơ chế kết nối trực tiếp:

1.  **Gateway Service:** Sử dụng **Static Routing** (Định tuyến tĩnh) trong `application.yml` để trỏ trực tiếp đến các service con (`localhost:8081`, `localhost:8082`...).
2.  **Inter-service Communication:** Các Service giao tiếp với nhau qua **OpenFeign** được cấu hình URL cứng (Hard-coded URL) thay vì lookup qua Service ID.

👉 *Mục đích: Giúp hệ thống gọn nhẹ, dễ dàng chạy demo trên máy cá nhân mà không cần khởi động quá nhiều hạ tầng phức tạp.*

---

## 🚀 Công nghệ sử dụng

* **Java**: JDK 17 (hoặc 21).
* **Framework**: Spring Boot 3.2.x.
* **Gateway**: Spring Cloud Gateway (Netty - Reactive).
* **Database**: H2 Database (In-Memory) - *Dữ liệu sẽ tự reset về mặc định mỗi khi tắt ứng dụng.*
* **Frontend**: HTML5, Bootstrap 5, Javascript Fetch API (Tích hợp sẵn trong Gateway).

---

## 📂 Danh sách Service

Hệ thống bao gồm 4 Service độc lập:

| Service Name | Port | Chức năng | Database |
| :--- | :--- | :--- | :--- |
| **gateway-service** | `8080` | **Cổng chính (Entry Point)**. Chứa giao diện Web UI. | N/A |
| **book-service** | `8081` | Quản lý kho sách (CRUD). | H2 (`library_db`) |
| **user-service** | `8082` | Quản lý độc giả (CRUD). | H2 (`library_user_db`) |
| **borrow-service** | `8083` | Nghiệp vụ Mượn trả (Gọi sang Book & User). | H2 (`library_borrow_db`) |

---

## 🛠️ Hướng dẫn Cài đặt & Chạy code (Run)

### 1. Chuẩn bị môi trường
* Java Development Kit (JDK) 17 trở lên.
* Maven.
* IDE: Spring Tool Suite 4 (STS), IntelliJ IDEA hoặc Eclipse.

### 2. Tải mã nguồn:
### 3. Khởi chạy hệ thống (Quan trọng)
Để hệ thống hoạt động trơn tru và tránh lỗi kết nối (Connection Refused), bạn **cần** khởi động các service theo thứ tự ưu tiên sau:

* **Bước 1: Chạy Service cơ sở (Chứa dữ liệu)**
    * 🟢 Start `book-service` (Port 8081)
    * 🟢 Start `user-service` (Port 8082)
    * *(Chờ khoảng 10-15s để 2 service này khởi động xong)*

* **Bước 2: Chạy Service nghiệp vụ**
    * 🟢 Start `borrow-service` (Port 8083)
    * *(Service này cần kết nối tới Book và User để kiểm tra tồn kho)*

* **Bước 3: Chạy Cổng giao tiếp & Giao diện**
    * 🟢 Start `gateway-service` (Port 8080)
    * *(Đây là cổng duy nhất mở ra cho người dùng truy cập)*

💡 **Mẹo:** Nếu bạn dùng **Spring Tool Suite (STS)**, hãy mở tab **Boot Dashboard**, chọn cả 4 project (nhấn Shift + Click) và nhấn nút **(Re)start** để chạy đồng loạt.

---

## 🌐 Hướng dẫn Sử dụng Demo (UI)

Sau khi cả 4 service đã chạy (Console báo Started thành công), bạn thực hiện các bước sau để trải nghiệm:

### 1. Truy cập Web Dashboard
Mở trình duyệt (Chrome/Edge/Firefox) và truy cập đường dẫn duy nhất:
👉 **[http://localhost:8080](http://localhost:8080)**

*(Lưu ý: Không truy cập trực tiếp các cổng 8081, 8082 vì giao diện chỉ nằm ở Gateway 8080)*

### 2. Đăng nhập
Hệ thống có sẵn dữ liệu mẫu trong Memory. Bạn có thể sử dụng tài khoản sau:
* **Username:** `nguyenvana`
* **Password:** (Hệ thống demo không yêu cầu mật khẩu)
* *Hoặc nhấn nút **"Đăng ký thành viên mới"** để tạo user riêng.*

### 3. Trải nghiệm chức năng
1.  **Trang chủ:** Xem thống kê tổng quan số lượng sách và độc giả hiện có.
2.  **Quản lý Sách:** Thêm sách mới, Sửa thông tin, Xóa sách (Dữ liệu sẽ được lưu vào `book-service`).
3.  **Mượn trả:**
    * Vào menu "Mượn trả".
    * Chọn Độc giả và Sách từ danh sách thả xuống.
    * Nhấn "Xác nhận mượn". Hệ thống sẽ gọi API sang `borrow-service` -> `book-service` để trừ số lượng tồn kho.

---

## 🧪 API Endpoints (Dành cho Postman)

Nếu muốn test Backend độc lập mà không dùng giao diện Web, bạn hãy gọi API qua cổng Gateway **8080**:

* **Lấy danh sách sách:** `GET http://localhost:8080/books`
* **Lấy danh sách user:** `GET http://localhost:8080/users`
* **Mượn sách:**
    * URL: `POST http://localhost:8080/borrows`
    * Body (JSON):
        ```json
        {
          "userId": 1,
          "bookId": 1
        }
        ```
* **Xem lịch sử mượn:** `GET http://localhost:8080/borrows/user/{userId}`

---
