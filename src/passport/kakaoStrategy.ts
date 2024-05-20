import { PassportStatic } from "passport";
import User from "../models/schemas/User";
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
            async (
                accessToken: string,
                refreshToken: string,
                profile: ProfileType,
                done: (err: Error | null | unknown, user?: string | null, info?: any) => void
            ) => {
                try {
                    const userInfo = await User.findOne({ id: profile.id });
                    if (userInfo) {
                        return done(null, profile.id);
                    } else {
                        console.log(profile);

                        const newUser = new User({
                            id: profile.id,
                            // email: profile._json.email,
                            profile_image: profile._json.properties.profile_image,
                            nickname: profile._json.properties.nickname,
                            // name: profile._json.name,
                            type: profile.provider,
                        });
                        const res = await newUser.save();

                        if (res) {
                            return done(null, profile.id);
                        } else {
                            return done(null, null);
                        }
                    }
                } catch (error) {
                    console.log(error);
                    done(error);
                }
            }
        )
    );
};

export default kakaoPassportConfig;
