CREATE LOGIN QLthuchi WITH PASSWORD = '11111';
GO
USE QLthuchi;
GO
CREATE USER QLthuchi FOR LOGIN QLthuchi;
GO
ALTER ROLE db_owner ADD MEMBER QLthuchi;
GO
CREATE TABLE VaiTro (
    ma_vai_tro INT IDENTITY(1,1) PRIMARY KEY,
    ten_vai_tro NVARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE NguoiDung (
    ma_nguoi_dung INT IDENTITY(1,1) PRIMARY KEY,
    ten_dang_nhap VARCHAR(50) UNIQUE NOT NULL,
    mat_khau VARCHAR(255) NOT NULL,
    ho_ten NVARCHAR(100) NOT NULL,
    thu_dien_tu VARCHAR(100) UNIQUE NOT NULL,
    so_dien_thoai VARCHAR(20),
    trang_thai BIT DEFAULT 1,
    ngay_tao DATETIME DEFAULT GETDATE()
);


CREATE TABLE NguoiDung_VaiTro (
    ma_nguoi_dung INT FOREIGN KEY REFERENCES NguoiDung(ma_nguoi_dung) ON DELETE CASCADE,
    ma_vai_tro INT FOREIGN KEY REFERENCES VaiTro(ma_vai_tro) ON DELETE CASCADE,
    PRIMARY KEY (ma_nguoi_dung, ma_vai_tro)
);

CREATE TABLE ViTien (
    ma_vi INT IDENTITY(1,1) PRIMARY KEY,
    ma_nguoi_dung INT FOREIGN KEY REFERENCES NguoiDung(ma_nguoi_dung) ON DELETE CASCADE,
    ten_vi NVARCHAR(100) NOT NULL,
    so_du DECIMAL(18, 2) DEFAULT 0.00 NOT NULL,
    don_vi_tien_te NVARCHAR(10) DEFAULT N'VNĐ',
    bieu_tuong NVARCHAR(50)
);

CREATE TABLE DanhMuc (
    ma_danh_muc INT IDENTITY(1,1) PRIMARY KEY,
    ma_nguoi_dung INT FOREIGN KEY REFERENCES NguoiDung(ma_nguoi_dung) ON DELETE CASCADE,
    ten_danh_muc NVARCHAR(100) NOT NULL,
    loai_danh_muc NVARCHAR(10) CHECK (loai_danh_muc IN (N'THU', N'CHI')) NOT NULL,
    bieu_tuong NVARCHAR(50)
);

CREATE TABLE GiaoDich (
    ma_giao_dich INT IDENTITY(1,1) PRIMARY KEY,
    ma_nguoi_dung INT FOREIGN KEY REFERENCES NguoiDung(ma_nguoi_dung) ON DELETE CASCADE,
    ma_vi INT FOREIGN KEY REFERENCES ViTien(ma_vi) ON DELETE NO ACTION,
    ma_danh_muc INT FOREIGN KEY REFERENCES DanhMuc(ma_danh_muc) ON DELETE NO ACTION,
    so_tien DECIMAL(18, 2) NOT NULL CHECK (so_tien > 0),
    loai_giao_dich NVARCHAR(20) CHECK (loai_giao_dich IN (N'THU', N'CHI', N'CHUYEN_TIEN')) NOT NULL,
    ma_vi_dich INT NULL FOREIGN KEY REFERENCES ViTien(ma_vi),
    ngay_giao_dich DATETIME NOT NULL,
    ghi_chu NVARCHAR(255),
    ngay_tao DATETIME DEFAULT GETDATE()
);
CREATE TABLE NganSach (
    ma_ngan_sach INT IDENTITY(1,1) PRIMARY KEY,
    ma_nguoi_dung INT FOREIGN KEY REFERENCES NguoiDung(ma_nguoi_dung) ON DELETE CASCADE,
    ma_danh_muc INT FOREIGN KEY REFERENCES DanhMuc(ma_danh_muc) ON DELETE NO ACTION,
    so_tien_han_muc DECIMAL(18, 2) NOT NULL CHECK (so_tien_han_muc > 0),
    thang INT CHECK (thang BETWEEN 1 AND 12) NOT NULL,
    nam INT NOT NULL
);

CREATE TABLE MucTieuTietKiem (
    ma_muc_tieu INT IDENTITY(1,1) PRIMARY KEY,
    ma_nguoi_dung INT FOREIGN KEY REFERENCES NguoiDung(ma_nguoi_dung) ON DELETE CASCADE,
    ten_muc_tieu NVARCHAR(100) NOT NULL,
    so_tien_muc_tieu DECIMAL(18, 2) NOT NULL CHECK (so_tien_muc_tieu > 0),
    so_tien_hien_tai DECIMAL(18, 2) DEFAULT 0.00 NOT NULL,
    ngay_bat_dau DATE NOT NULL,
    ngay_ket_thuc DATE NOT NULL,
    trang_thai NVARCHAR(20) DEFAULT N'DANG_THUC_HIEN' CHECK (trang_thai IN (N'DANG_THUC_HIEN', N'HOAN_THANH', N'HUY_BO'))
);

CREATE TABLE GiaoDichDinhKy (
    ma_dinh_ky INT IDENTITY(1,1) PRIMARY KEY,
    ma_nguoi_dung INT FOREIGN KEY REFERENCES NguoiDung(ma_nguoi_dung) ON DELETE CASCADE,
    ma_vi INT FOREIGN KEY REFERENCES ViTien(ma_vi) ON DELETE NO ACTION,
    ma_danh_muc INT FOREIGN KEY REFERENCES DanhMuc(ma_danh_muc) ON DELETE NO ACTION,
    so_tien DECIMAL(18, 2) NOT NULL,
    loai_giao_dich NVARCHAR(10) CHECK (loai_giao_dich IN (N'THU', N'CHI')) NOT NULL,
    tan_suat NVARCHAR(20) CHECK (tan_suat IN (N'HANG_NGAY', N'HANG_TUAN', N'HANG_THANG', N'HANG_NAM')) NOT NULL,
    ngay_thuc_hien_tiep_theo DATE NOT NULL,
    ghi_chu NVARCHAR(255)
);

CREATE TABLE ThongBao (
    ma_thong_bao INT IDENTITY(1,1) PRIMARY KEY,
    ma_nguoi_dung INT FOREIGN KEY REFERENCES NguoiDung(ma_nguoi_dung) ON DELETE CASCADE,
    tieu_de NVARCHAR(150) NOT NULL,
    noidung NVARCHAR(MAX) NOT NULL,
    da_doc BIT DEFAULT 0,
    ngay_tao DATETIME DEFAULT GETDATE()
);
