import { PassportStatic } from "passport";
const GoogleStrategy = require('passport-google-oauth20').Strategy;

interface User {
    id: string;
    // 다른 사용자 정보 필드들...
}

var passportConfig = function(passport: PassportStatic){
    passport.use(new GoogleStrategy(
        {
            clientID: process.env.GOOGLE_CLIENT_ID,
            clientSecret: process.env.GOOGLE_CLIENT_SECRET,
            callbackURL: process.env.GOOGLE_CALLBACK_URL,
        },
        (accessToken: string, refreshToken: string, profile: object, done: (err: Error | null, user?: User | null, info?: any) => void) => {
            done(null, profile as User); // profile을 User 타입으로 캐스팅하여 전달
        }
    ));

    passport.serializeUser(function(user: User, done: (err: Error | null, id?: string) => void) {
        done(null, user.id);
    });
}

module.exports = passportConfig;