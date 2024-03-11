import { PassportStatic } from "passport";
const GoogleStrategy = require("passport-google-oauth20").Strategy;

type ProfileType = {
    id: string;
    provider: string;
    _json: {
        name: string;
        email: string;
        picture: string;
    };
};

var googlePassportConfig = function (passport: PassportStatic) {
    passport.use(
        new GoogleStrategy(
            {
                clientID: process.env.GOOGLE_CLIENT_ID,
                clientSecret: process.env.GOOGLE_CLIENT_SECRET,
                callbackURL: process.env.GOOGLE_CALLBACK_URL,
            },
            (accessToken: string, refreshToken: string, profile: ProfileType, done: (err: Error | null, user?: string | null, info?: any) => void) => {
                // 구글 로그인 성공 시 데이터 생성
                const userData = {
                    googleId: profile.id,
                    name: profile._json.name,
                    email: profile._json.email,
                    profile: profile._json.picture,
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

export default googlePassportConfig;
