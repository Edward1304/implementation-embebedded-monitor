//
// Created by jbgomezm on 10/27/23.
//
/
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <chrono>
#include <string.h>

using namespace std;

/* struct tm {
   int tm_sec;
   int tm_min;
   int tm_hour;
   int tm_mday;
   int tm_mon;
   int tm_year;
}; */

char *asctime(const struct tm *timeptr)
{
    static const char mon_name[][4] = {
        "01", "02", "03", "04", "05", "06",
        "07", "08", "09", "10", "11", "12"};
    static char result[26];
    sprintf(result, " %d-%.3s-%2d %.2d:%.2d:%.2d",
            1900 + timeptr->tm_year,
            mon_name[timeptr->tm_mon],
            timeptr->tm_mday, timeptr->tm_hour,
            timeptr->tm_min, timeptr->tm_sec);
    return result;
}

int main()
{
    ifstream file;
    double usage;
    time_t last_time;
    struct tm *time_now;

    while (1)
    {
        auto now = chrono::system_clock::now();
        time_t end_time = chrono::system_clock::to_time_t(now);
        time_now = gmtime(&end_time);
        char *str_time = asctime(time_now);

        if (end_time - last_time >= 2)
        {
            cout << "Current Time and Date: " << str_time << endl;

            file.open("/proc/stat"); // Abre la carpeta
            if (file.is_open())
            { // Si la puede abrir correctamente, ingresa
                string line;
                if (getline(file, line))
                {
                    file.close();
                    istringstream iss(line);
                    int user, nice, system, idle, iowait, irq, softirq,
                        steal, guest, guest_nice;
                    string cpu;
                    iss >> cpu >> user >> nice >> system >> idle >> iowait >> irq >>
                        softirq >> steal >> guest >> guest_nice;
                    usage = 100.0 * (1 - double(idle) / double(user + nice +
                                                               system + idle + iowait + irq + softirq + steal + guest +
                                                               guest_nice));

                    cout << "CPU usage: " << usage << "\%" << endl;
                }
                else
                {
                    cout << "Error: couldn't read line from file." << endl;
                    file.close();
                }
            }
            else
                cout << "Error: /proc/stat couldn't be openned." << endl;

            // Organizar datos en string
            string data;
            string str_CPU = to_string(usage);
            ostringstream ss;
            ss << str_time << ", " << str_CPU;
            data= ss.str();
            cout << data << endl;

            last_time = end_time;
        }
    }
    return 0;
}

//netcat
//double usage
// lib socket dev "++" loopback: 127.0.0.1   nc -l 127.0.0.1 puerto
/