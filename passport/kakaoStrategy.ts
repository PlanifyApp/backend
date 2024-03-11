import { PassportStatic } from "passport";
const KakaoStrategy = require("passport-kakao").Strategy;

type ProfileType = {
    id: string;
    provider: string;
    _json: {
        properties: {
            nickname: string;
            profile_image: string;
        };
    };
};

var kakaoPassportConfig = function (passport: PassportStatic) {
    passport.use(
        new KakaoStrategy(
            {
                clientID: process.env.KAKAO_CLIENT_ID,
                callbackURL: process.env.KAKAO_CALLBACK_URL,
            },
            (accessToken: string, refreshToken: string, profile: ProfileType, done: (err: Error | null, user?: string | null, info?: any) => void) => {
                // 카카오 로그인 성공 시 데이터 생성
                console.log(profile);
                const userData = {
                    kakaoId: profile.id,
                    name: profile._json.properties.nickname,
                    profile: profile._json.properties.profile_image,
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

export default kakaoPassportConfig;
