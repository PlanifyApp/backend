import mongoose, { Schema } from "mongoose";
const {
    Types: { ObjectId },
} = Schema;

export interface Schedule extends Document {
    id: number;
    title: string;
    memo: string;
    groupId: number;
    startDate: Date;
    endDate: Date;
    userId: typeof ObjectId;
    createAt: Date;
    modifiedAt: Date;
}

const ScheduleSchema: Schema = new Schema({
    title: {
        type: String,
        required: true,
    },
    memo: {
        type: String,
        required: false,
    },
    groupId: {
        type: Number,
        required: false,
    },
    startDate: {
        type: Date,
        required: true,
    },
    endDate: {
        type: Date,
        required: true,
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

const Schedule = mongoose.model<Schedule>("Schedule", ScheduleSchema);
export default Schedule;
