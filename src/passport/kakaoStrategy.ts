import { PassportStatic } from "passport";
import User from "../models/schemas/User";
const KakaoStrategy = require("passport-kakao").Strategy;

type ProfileType = {
    id: string;
    provider: string;
    username: string;
    _json: {
        properties: {
            profile_image: string;
        };
    };
};

const kakaoPassportConfig = new KakaoStrategy(
    {
        clientID: process.env.KAKAO_CLIENT_ID,
        callbackURL: process.env.KAKAO_CALLBACK_URL,
    },
    async (accessToken: string, refreshToken: string, profile: ProfileType, cb: any) => {
        try {
            const user = await User.findOne({ id: profile.id });

            if (user) {
                return cb(null, user);
            } else {
                const newUser = new User({
                    id: profile.id,
                    // email: profile._json.email,
                    profile_image: profile._json.properties.profile_image,
                    // nickname: profile._json.properties.nickname,
                    name: profile.username,
                    type: profile.provider,
                });
                const res = await newUser.save();

                if (res) {
                    return cb(null, newUser);
                } else {
                    return cb(null, null);
                }
            }
        } catch (error) {}
    }
);

export default kakaoPassportConfig;
