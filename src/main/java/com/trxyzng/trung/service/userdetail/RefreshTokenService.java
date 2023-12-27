package com.trxyzng.trung.service.userdetail;

import com.trxyzng.trung.entity.RefreshToken;
import com.trxyzng.trung.repository.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.ArrayList;

@Service
public class RefreshTokenService {
    @Autowired
    RefreshTokenRepo refreshTokenRepo;
    public RefreshToken LoadRefreshTokenByRefreshToken(String token) {
        return refreshTokenRepo.findByRefreshToken(token);
    }
    public void SaveRefreshToken(RefreshToken token) {
        refreshTokenRepo.save(token);
    }
    public ArrayList<RefreshToken> LoadRefreshTokenById(int id) {
        return refreshTokenRepo.findRefreshTokenById(id);
    }
}
