import mongoose, { Schema } from "mongoose";
const {
    Types: { ObjectId },
} = Schema;

interface Group extends Document {
    title: string;
    color: string;
    isShow: string;
    userId: typeof ObjectId;
    createAt: Date;
    modifiedAt: Date;
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
    isShow: {
        type: String,
        required: false,
        default: "Y",
    },
    userId: {
        type: ObjectId,
        required: true,
        ref: "User",
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

const Group = mongoose.model<Group>("Group", GroupSchema);
export default Group;
