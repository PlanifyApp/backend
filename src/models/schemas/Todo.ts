import mongoose, { Schema } from "mongoose";

export interface Todo extends Document {
    id: number;
    title: string;
    is_show: string;
}

const TodoSchema: Schema = new Schema({
    id: {
        type: Number,
        required: true,
        unique: true,
    },
    title: {
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

const Todo = mongoose.model<Todo>("Todo", TodoSchema);
export default Todo;
