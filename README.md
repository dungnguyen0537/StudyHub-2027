# 🦉 StudyHub - Ứng dụng Quản lý Học tập Toàn diện

**StudyHub** là một ứng dụng di động hỗ trợ quản lý học tập toàn diện dành cho học sinh/sinh viên. Ứng dụng được thiết kế theo kiến trúc **MVVM** hiện đại, hoạt động theo cơ chế **Offline-First** (lưu trữ cục bộ với Room Database) và đồng bộ hóa đám mây ngầm (Cloud Sync) thông qua Firebase Firestore.

Dưới đây là chi tiết các phân hệ (modules) và chức năng chính của ứng dụng:

---

## 🚀 Các Tính Năng Nổi Bật (Mới Cập Nhật)
- **Onboarding Screen (Màn hình Chào mừng):** Trải nghiệm người dùng mượt mà ngay từ lần mở đầu tiên với các slide giới thiệu tính năng.
- **Biểu Đồ Thống Kê Năng Suất (Analytics):** Trực quan hóa dữ liệu học tập thông qua biểu đồ tròn (Pie Chart) đánh giá tiến độ Task và biểu đồ cột (Bar Chart) phân tích Deadline bằng thư viện MPAndroidChart.
- **Đồng Hồ Tập Trung (Pomodoro):** Tích hợp phương pháp học tập Pomodoro 25 phút không xao nhãng, có phản hồi rung (Vibration Feedback) khi kết thúc phiên học.
- **Vuốt Để Xóa (Swipe-to-Delete) & Hoàn Tác (Undo):** Trải nghiệm UX cực tốt khi vuốt để xóa mọi bản ghi (Task, Deadline, Môn học, Ghi chú), hỗ trợ khôi phục tức thì qua Snackbar.
- **Trạng Thái Rỗng (Empty States):** Hiển thị màn hình chờ trực quan với logo Cú Xanh khi danh sách chưa có dữ liệu, thay vì khoảng trắng nhàm chán.

---

## 1. Xác thực và Tài khoản (Authentication)
Quản lý luồng đăng nhập và bảo mật người dùng thông qua Firebase Authentication.
- **Đăng nhập / Đăng ký:** Hỗ trợ tạo tài khoản mới bằng Email/Password hoặc **tích hợp xác thực nhanh bằng tài khoản Google (Google Sign-In)**.
- **Xác thực Email Chuyên Nghiệp:** Email xác thực được chứng nhận bảo mật bằng DKIM, SPF, DMARC từ tên miền Custom Domain, chống vào thư rác 100%.
- **Quản lý phiên đăng nhập:** Tự động giữ phiên đăng nhập (Auto-login) nếu người dùng đã từng đăng nhập trước đó.

## 2. Bảng Điều Khiển (Dashboard)
Màn hình trung tâm (Home) hiển thị bức tranh toàn cảnh về tiến độ học tập trong ngày.
- **Card Tóm tắt:** Thống kê số lượng Môn học và Công việc. Cung cấp lối tắt truy cập nhanh vào **Thống kê (Analytics)** và **Pomodoro**.
- **Tóm tắt Lịch học hôm nay:** Hiển thị danh sách Lịch học sắp tới trong ngày, hỗ trợ Swipe-to-delete.

## 3. Quản lý Môn học (Subjects)
Phân hệ nền tảng, mọi Lịch học, Deadline hay Ghi chú đều được liên kết với một Môn học cụ thể.
- **Thông tin Môn học:** Tên môn, Giảng viên, Số tín chỉ.
- **Gắn màu sắc (Color Coding):** Tùy chỉnh màu sắc riêng biệt cho từng môn học để dễ nhận diện trên các danh sách và Widget.

## 4. Quản lý Lịch học (Schedules)
Công cụ sắp xếp thời khóa biểu hàng tuần.
- **Thiết lập linh hoạt:** Chọn ngày trong tuần, phòng học, thời gian bắt đầu & kết thúc.
- **Nhắc nhở tự động (Push Notifications):** Đẩy thông báo báo thức tự động tới thiết bị trước giờ học để đảm bảo bạn không đến trễ.

## 5. Quản lý Hạn chót (Deadlines)
- **Thuộc tính chi tiết:** Tiêu đề, Mô tả, Ngày giờ hết hạn (dueDate), Mức độ ưu tiên (Đỏ/Vàng/Xanh).
- **Phân loại theo Môn học:** Liên kết trực tiếp deadline với môn học.
- **Báo thức Hạn chót:** Tự động báo thức đẩy cảnh báo 24 giờ trước khi Deadline "dí".

## 6. Quản lý Công việc nhỏ (Tasks)
Dành cho To-do list hằng ngày độc lập.
- **Đánh dấu Trạng thái:** Click để hoàn thành (Checkbox).
- **Quản lý Vòng đời:** Dễ dàng tạo mới và vuốt để xóa bỏ.

## 7. Quản lý Ghi chú (Notes)
Không gian lưu trữ kiến thức và tài liệu.
- Viết ghi chú tự do, liên kết với môn học và tính năng **Đánh dấu Yêu thích (Favorite)** để đưa ghi chú quan trọng lên đầu danh sách.

## 8. Widget Màn hình chính (Home Screen Widget)
Tiện ích theo dõi tiến độ ngay từ màn hình chính của điện thoại.
- **Thiết kế phân chia (Split Layout):** 
  - Bên trái: Lịch học trong ngày.
  - Bên phải: Danh sách Deadline cận kề.
- **Real-time Update:** Tự động đồng bộ với dữ liệu trong App.

## 9. Cơ chế Đồng bộ Đám mây (Cloud Syncing)
Điểm mạnh nhất về mặt kỹ thuật của StudyHub là kiến trúc **Offline-First**.
- **Hoạt động 100% Offline:** Dữ liệu phản hồi tức thì nhờ thao tác trực tiếp trên `Room Database` (SQLite).
- **Background Sync:** Sử dụng `WorkManager` và `SyncManager` để âm thầm đồng bộ hai chiều (2-way sync) với `Firebase Firestore` khi có mạng.
- **Tránh Xung Đột Dữ Liệu:** Hệ thống xử lý Foreign Key thông minh, đảm bảo không gặp lỗi "dữ liệu ma" (Ghost data) khi đồng bộ.

---

## ⚙️ Yêu Cầu Hệ Thống (System Requirements)

Để biên dịch và chạy được ứng dụng **StudyHub** từ mã nguồn, máy tính của bạn cần đáp ứng các điều kiện sau:

### Phía Nhà Phát Triển (Developer Environment)
- **IDE:** Android Studio Iguana (2023.2.1) hoặc mới hơn (Bản hiện tại đang sử dụng Koala).
- **JDK:** Java Development Kit (JDK) 17 trở lên.
- **Gradle:** Phiên bản Gradle 8.2 trở lên (Tích hợp sẵn trong Gradle Wrapper).
- **Hệ Điều Hành:** Windows 10/11, macOS (Intel/Apple Silicon), hoặc Linux.
- **Bộ Nhớ RAM:** Khuyến nghị tối thiểu 8GB (Tốt nhất là 16GB) để chạy mượt Android Emulator.

### Phía Thiết Bị Chạy Ứng Dụng (End-User Device)
- **Hệ Điều Hành Hỗ Trợ:** Android 8.0 (Oreo - API Level 26) trở lên.
- **Kết Nối Mạng:** Yêu cầu có Internet trong lần đầu tiên mở app để Đăng nhập/Đăng ký. Sau đó có thể sử dụng Offline hoàn toàn.
- **Dịch vụ Google Play:** Bắt buộc phải có Google Play Services trên thiết bị để hỗ trợ Firebase Auth (Google Sign-In), Cloud Messaging (Thông báo) và Firestore.

---

*Phát triển bởi Đội ngũ StudyHub - 2026.*
