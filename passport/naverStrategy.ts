import { PassportStatic } from "passport";
const NaverStrategy = require("passport-naver").Strategy;

type ProfileType = {
    id: string;
    provider: string;
    _json: {
        id: string;
        nickname: string;
        email: string;
        profile_image: string;
    };
};

var naverPassportConfig = function (passport: PassportStatic) {
    passport.use(
        new NaverStrategy(
            {
                clientID: process.env.NAVER_CLIENT_ID,
                clientSecret: process.env.NAVER_CLIENT_SECRET,
                callbackURL: process.env.NAVER_CALLBACK_URL,
            },
            (accessToken: string, refreshToken: string, profile: ProfileType, done: (err: Error | null, user?: string | null, info?: any) => void) => {
                // 네이버 로그인 성공 시 데이터 생성
                const userData = {
                    naverId: profile.id,
                    name: profile._json.nickname,
                    email: profile._json.email,
                    profile: profile._json.profile_image,
                    type: profile.provider,
                };

                done(null, profile.id);
                // User.findOrCreate((userData, function(err, user){
                //     return cb(err, user)
                // }));
            }
        )
    );
};

export default naverPassportConfig;
