import mongoose, { Schema } from "mongoose";

interface User extends Document {
    id: string;
    email: string;
    profileImage: string;
    nickname: string;
    name: string;
    type: string;
    createAt: Date;
    modifiedAt: Date;
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
    profileImage: {
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
    createAt: {
        type: Date,
        default: Date.now,
    },
    modifiedAt: {
        type: Date,
        default: Date.now,
    },
});

const User = mongoose.model<User>("User", UserSchema);
export default User;
