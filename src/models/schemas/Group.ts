import mongoose, { Schema } from "mongoose";

interface Group extends Document {
    title: string;
    name: string;
    color: string;
    is_show: string;
}

const GroupSchema: Schema = new Schema({
    title: {
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
    user_id: {
        type: String,
        required: true,
    },
});

const Group = mongoose.model<Group>("Group", GroupSchema);
export default Group;
