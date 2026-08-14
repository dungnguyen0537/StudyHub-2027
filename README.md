# Tổng quan Dự án StudyHub
**StudyHub** là một ứng dụng di động hỗ trợ quản lý học tập toàn diện dành cho học sinh/sinh viên. Ứng dụng được thiết kế theo kiến trúc **MVVM** hiện đại, hoạt động theo cơ chế **Offline-First** (lưu trữ cục bộ với Room Database) và đồng bộ hóa đám mây ngầm (Cloud Sync) thông qua Firebase Firestore.

Dưới đây là chi tiết các phân hệ (modules) và chức năng chính của ứng dụng:

---

## 1. Xác thực và Tài khoản (Authentication)
Quản lý luồng đăng nhập và bảo mật người dùng thông qua Firebase Authentication.
- **Đăng nhập / Đăng ký:** Hỗ trợ tạo tài khoản mới bằng Email/Password hoặc **tích hợp xác thực nhanh bằng tài khoản Google (Google Sign-In)**.
- **Quên mật khẩu:** Gửi email khôi phục mật khẩu.
- **Quản lý phiên đăng nhập:** Tự động giữ phiên đăng nhập (Auto-login) nếu người dùng đã từng đăng nhập trước đó. Màn hình Splash Screen sẽ làm nhiệm vụ điều hướng.

## 2. Bảng Điều Khiển (Dashboard)
Màn hình trung tâm (Home) hiển thị bức tranh toàn cảnh về tiến độ học tập trong ngày.
- **Trạng thái hôm nay:** Hiển thị số lượng Lịch học, Deadline cấp bách và Task cần hoàn thành trong ngày.
- **Truy cập nhanh (Quick Actions):** Các nút bấm tạo nhanh Lịch học, Task, Deadline.
- **Tóm tắt Lịch học & Task:** Hiển thị ngay lập tức danh sách các môn phải học và các công việc nhỏ cần làm hôm nay. Có thao tác vuốt để hoàn thành/xóa nhanh.

## 3. Quản lý Môn học (Subjects)
Phân hệ nền tảng, mọi Lịch học, Deadline hay Ghi chú đều được liên kết với một Môn học cụ thể.
- **Thêm/Sửa/Xóa môn học:** Nhập tên môn học, giảng viên phụ trách, số tín chỉ.
- **Gắn màu sắc (Color Coding):** Tùy chỉnh màu sắc riêng biệt cho từng môn học để dễ nhận diện trên các danh sách và Widget.
- **Thống kê môn học:** Nhóm các tài liệu, lịch và deadline lại theo từng môn.

## 4. Quản lý Lịch học (Schedules)
Công cụ sắp xếp thời khóa biểu hàng tuần.
- **Thêm/Sửa/Xóa lịch học:** Chọn môn học, ngày trong tuần (Thứ 2 - Chủ Nhật), thời gian bắt đầu, thời gian kết thúc và Phòng học.
- **Lặp lại hàng tuần:** Lịch học được tự động thiết lập cố định vào các ngày trong tuần.
- **Nhắc nhở tự động (Push Notifications):** Đẩy thông báo báo thức tự động tới thiết bị trước giờ học (có thể cài đặt trước 15p, 30p...).

## 5. Quản lý Hạn chót (Deadlines)
Theo dõi các bài tập lớn, tiểu luận hoặc kỳ thi quan trọng.
- **Quản lý Deadline:** Thêm tiêu đề, mô tả chi tiết, ngày giờ đến hạn, mức độ ưu tiên (Cao/Trung bình/Thấp) và Trạng thái (Đang làm/Hoàn thành).
- **Phân loại theo Môn học:** Liên kết trực tiếp deadline với Môn học để dễ truy xuất.
- **Báo thức Hạn chót:** Tự động lên lịch đẩy thông báo cảnh báo trước 24 giờ so với giờ `dueDate` để tránh trễ hạn.

## 6. Quản lý Công việc nhỏ (Tasks)
Dành cho các công việc không gắn liền với môn học cụ thể (To-do list hằng ngày).
- **Thêm Task:** Nhập tên công việc, độ ưu tiên, deadline trong ngày.
- **Trạng thái:** Đánh dấu hoàn thành bằng Checkbox.
- **Swipe-to-Delete:** Hỗ trợ thao tác vuốt sang trái/phải để xóa công việc, kèm theo thanh thông báo (Snackbar) cho phép "Hoàn tác" (Undo) nếu lỡ xóa nhầm.

## 7. Quản lý Ghi chú (Notes)
Không gian lưu trữ kiến thức và tài liệu.
- **Trình soạn thảo văn bản:** Viết ghi chú chi tiết, lưu lại ý tưởng môn học.
- **Phân loại & Đánh dấu:** Gắn ghi chú với môn học tương ứng, có chức năng đánh dấu Yêu thích (Favorite - hình Ngôi sao) để đưa lên đầu danh sách.

## 8. Widget Màn hình chính (Home Screen Widget)
Tiện ích theo dõi tiến độ ngay từ màn hình chính của điện thoại mà không cần mở ứng dụng.
- **Thiết kế chia đôi (Split Layout):** Widget được chia làm 2 cột:
  - **Cột trái:** Danh sách *Lịch học* trong ngày hôm nay kèm theo phòng học và thời gian.
  - **Cột phải:** Danh sách các *Deadline* chưa hoàn thành và sắp đến hạn.
- **Đồng bộ Real-time:** Dữ liệu tự động cập nhật lại mỗi khi người dùng thay đổi trong ứng dụng.

## 9. Hồ sơ Người dùng (User Profile)
- Quản lý thông tin cá nhân.
- (Đang phát triển) Cập nhật ảnh đại diện, đổi tên, và đăng xuất tài khoản an toàn khỏi thiết bị.

## 10. Cơ chế Đồng bộ Đám mây (Cloud Syncing)
Điểm mạnh nhất về mặt kỹ thuật của StudyHub là cơ chế đồng bộ **Offline-First**.
- **Hoạt động không cần mạng:** Tất cả dữ liệu (CRUD) đều được thực thi và phản hồi lập tức trên `Room Database` (SQLite).
- **Đồng bộ nền (Background Sync):** Sử dụng `WorkManager` và `SyncManager` để âm thầm đẩy dữ liệu từ máy lên `Firebase Firestore` khi có kết nối Internet.
- **Bảo toàn dữ liệu:** Chống mất dữ liệu ngay cả khi người dùng đổi điện thoại hoặc lỡ tay gỡ cài đặt ứng dụng.
- **Quản lý xung đột (Foreign Key Handling):** Xử lý an toàn các lỗi ràng buộc dữ liệu ma khi tải dữ liệu từ đám mây xuống.
