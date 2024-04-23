import mongoose, { Schema } from "mongoose";

export interface Schedule extends Document {
    id: number;
    title: string;
    memo: string;
    group_id: number;
    start_date: Date;
    end_date: Date;
}

const ScheduleSchema: Schema = new Schema({
    id: {
        type: Number,
        required: true,
        unique: true,
    },
    title: {
        type: String,
        required: true,
    },
    memo: {
        type: String,
        required: false,
    },
    group_id: {
        type: Number,
        required: false,
    },
    start_date: {
        type: Date,
        required: true,
    },
    end_date: {
        type: Date,
        required: true,
    },
});

const Schedule = mongoose.model<Schedule>("Schedule", ScheduleSchema);
export default Schedule;
