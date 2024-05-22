import { PassportStatic } from "passport";
import User from "../models/schemas/User";
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

const naverPassportConfig = new NaverStrategy(
    {
        clientID: process.env.NAVER_CLIENT_ID,
        clientSecret: process.env.NAVER_CLIENT_SECRET,
        callbackURL: process.env.NAVER_CALLBACK_URL,
    },
    async (accessToken: string, refreshToken: string, profile: ProfileType, cb: any) => {
        try {
            const user = await User.findOne({ id: profile.id });

            if (user) {
                return cb(null, user);
            } else {
                const newUser = new User({
                    id: profile.id,
                    email: profile._json.email,
                    profile_image: profile._json.profile_image,
                    nickname: profile._json.nickname,
                    // name: profile._json.name,
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

export default naverPassportConfig;
