package com.example.service;

import com.example.dao.PublisherDao;
import com.example.model.Publisher;

import java.util.List;

public class PublisherService {

    private final PublisherDao publisherDao;

    public PublisherService() {
        this.publisherDao = new PublisherDao();
    }

    // Xem danh sách các nhà xuất bản
    public List<Publisher> getAllPublishers() {
        return publisherDao.getAllPublishers();
    }

    // Tìm kiếm nhà xuất bản theo tên
    public Publisher getPublisherByName(String publisherName) {
        return publisherDao.getPublisherByName(publisherName);
    }

    // Thêm một nhà xuất bản mới
    public boolean addPublisher(Publisher publisher) {
        return publisherDao.addPublisher(publisher);
    }

    // Cập nhật thông tin nhà xuất bản
    public boolean updatePublisher(Publisher publisher) {
        return publisherDao.updatePublisher(publisher);
    }

    // Tắt/ẩn nhà xuất bản
    public boolean deactivatePublisher(int publisherID) {
        return publisherDao.deactivatePublisher(publisherID);
    }

    // Bật/hiển thị nhà xuất bản
    public boolean activatePublisher(int publisherID) {
        return publisherDao.activatePublisher(publisherID);
    }

    public Publisher getPublisherById(int publisherID) {
        return publisherDao.getPublisherById(publisherID);
    }

    public List<Publisher> searchPublishers(String keyword) {
        return publisherDao.searchPublishers(keyword);
    }

    public List<Publisher> sortPublishers(String sortOrder) {
        return publisherDao.sortPublishers(sortOrder);
    }
    
 // Xem danh sách nhà xuất bản hoạt động
    public List<Publisher> getAllActivePublishers() {
        return publisherDao.getAllActivePublishers();
    }
}
