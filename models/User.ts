import mongoose, { Schema } from "mongoose";

export interface IUser extends Document {
    id: string;
    email: string;
    photo: string;
}

const UserSchema: Schema = new Schema({
    id: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    photo: {
        type: String,
        required: true
    }
})

const User = mongoose.model<IUser>('User', UserSchema);
export default User;