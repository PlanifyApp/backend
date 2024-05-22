import mongoose, { Schema } from "mongoose";

export interface Group extends Document {
    id: number;
    name: String;
    color: String;
    is_show: String;
}

const GroupSchema: Schema = new Schema({
    id: {
        type: Number,
        required: true,
        unique: true,
    },
    name: {
        type: String,
        required: true,
    },
    color: {
        type: String,
        required: true,
    },
    is_show: {
        type: String,
        required: false,
        default: "Y",
    },
});

const Group = mongoose.model<Group>("User", GroupSchema);
export default Group;
