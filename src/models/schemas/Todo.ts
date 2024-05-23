import mongoose, { Schema } from "mongoose";

interface Todo extends Document {
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
});

const Todo = mongoose.model<Todo>("Todo", TodoSchema);
export default Todo;
