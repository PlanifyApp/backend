import dotenv from 'dotenv';

var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var session = require('express-session');
var cors = require('cors');
var passport = require('passport');
import passportConfig from './passport/';

dotenv.config();

var indexRouter = require('./routes/index');
var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(cors());
app.use(session({
  secret: process.env.SESSION_SECRET_KEY,
  resave: false,
  saveUninitialized: false
}));

passportConfig(passport);
app.use(passport.initialize());
app.use(passport.session());

app.use('/api', indexRouter);

module.exports = app;
