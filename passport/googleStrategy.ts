const GoogleStrategy = require('passport-google-oauth20').Strategy;

module.exports = new GoogleStrategy(
    {
        clientID: process.env.GOOGLE_CLIENT_ID,
        clientSecret: process.env.GOOGLE_CLIENT_SECRET,
        callbackURL: process.env.GOOGLE_CALLBACK_URL,
    },
    (accessToken: string, refreshToken: string, profile: object, done: (err: Error | null, user?: any, info?: any) => void) => {
        console.log('세션 저장:', profile);
    }
)