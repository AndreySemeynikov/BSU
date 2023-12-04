#pragma once
#include <iostream>
#include <fstream>
#include <string>
#pragma warning(disable: 4996)

const int MAX_TEXT_LENGTH = 20;

struct message {
    char text[MAX_TEXT_LENGTH];

    message() {}

    message(std::string s) {
        if (s.length() > MAX_TEXT_LENGTH) {
            std::cout << "Message creating error";
            return;
        }
        strcpy(text, s.c_str());
    }

    char* get_text() {
        return text;
    }

    friend std::ostream& operator <<(std::ostream& out, message& m) {
        out.write((char*)&m, sizeof(m));
        return out;
    }

    friend std::istream& operator >>(std::istream& in, message& m) {
        in.read((char*)&m, sizeof(m));
        return in;
    }
};
