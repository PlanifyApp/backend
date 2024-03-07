import { PassportStatic } from "passport";
const GoogleStrategy = require('passport-google-oauth20').Strategy;

type ProfileType = {
    id: string;
    emails: {value: string, verified: boolean}[];
    photos: {value: string}[];
}

var googlePassportConfig = function(passport: PassportStatic){
    passport.use(new GoogleStrategy(
        {
            clientID: process.env.GOOGLE_CLIENT_ID,
            clientSecret: process.env.GOOGLE_CLIENT_SECRET,
            callbackURL: process.env.GOOGLE_CALLBACK_URL,
        },
        (accessToken: string, refreshToken: string, profile: ProfileType, done: (err: Error | null, user?: string | null, info?: any) => void) => {
            // User.findOrCreate(({ googleId: profile.id }, function(err, user){
            //     return cb(err, user)
            // }));
        }
    ));

    passport.serializeUser(function(user, done) {    
        console.log('serialize');
        console.log(user);
        done(null, user);
    });

    passport.deserializeUser(function(id, done) {
        // User.findById(id, function(err, user)){
        //     console.log(user);
        //     done(null, user);
        // }
    });
}

export default googlePassportConfig;