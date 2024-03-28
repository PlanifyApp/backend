import { PassportStatic } from "passport";
import User from "../models/User";
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
            async (accessToken: string, refreshToken: string, profile: ProfileType, done: (err: Error | null | unknown, user?: string | null, info?: any) => void) => {
                try {
                    const userInfo = await User.findOne({ id: profile.id });

                    if (userInfo) {
                        return done(null, profile.id);
                    } else {
                        const newUser = new User({
                            id: profile.id,
                            email: profile._json.email,
                            profile_image: profile._json.picture,
                            nickname: profile._json.name,
                            name: profile._json.name,
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

export default googlePassportConfig;
