import mongoose, { Schema } from "mongoose";

interface User extends Document {
    id: string;
    email: string;
    profile_image: string;
    nickname: string;
    name: string;
    type: string;
}

const UserSchema: Schema = new Schema({
    id: {
        type: String,
        required: true,
        unique: true,
    },
    email: {
        type: String,
        required: true,
    },
    profile_image: {
        type: String,
        required: false,
    },
    nickname: {
        type: String,
        required: false,
    },
    name: {
        type: String,
        required: false,
    },
    type: {
        type: String,
        required: true,
    },
});

const User = mongoose.model<User>("User", UserSchema);
export default User;
