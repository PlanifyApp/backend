import { PassportStatic } from "passport";
import User from "../models/schemas/User";
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

const googlePassportConfig = new GoogleStrategy(
    {
        clientID: process.env.GOOGLE_CLIENT_ID,
        clientSecret: process.env.GOOGLE_CLIENT_SECRET,
        callbackURL: process.env.GOOGLE_CALLBACK_URL,
        scope: ["email", "profile"],
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
                    profile_image: profile._json.picture,
                    nickname: profile._json.name,
                    name: profile._json.name,
                    type: profile.provider,
                });
                await newUser.save();
                return cb(null, newUser);
            }
        } catch (error) {}
    }
);

export default googlePassportConfig;
