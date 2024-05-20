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

var naverPassportConfig = function (passport: PassportStatic) {
    passport.use(
        new NaverStrategy(
            {
                clientID: process.env.NAVER_CLIENT_ID,
                clientSecret: process.env.NAVER_CLIENT_SECRET,
                callbackURL: process.env.NAVER_CALLBACK_URL,
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

export default naverPassportConfig;
