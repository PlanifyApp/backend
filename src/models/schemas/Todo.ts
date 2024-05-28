import mongoose, { Schema } from "mongoose";

interface Todo extends Document {
    title: string;
    is_show: string;
    user_id: string;
}

const TodoSchema: Schema = new Schema({
    title: {
        type: String,
        required: true,
    },
    date: {
        type: Date,
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
