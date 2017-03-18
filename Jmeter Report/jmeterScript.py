
def main():
    JDBCTime = []
    ServletTime = []
    f = open('jmeterTest.txt', 'r')
    for line in f:
        info = line.strip('\n').split('\t')
        JDBCTime.append(int(info[1]))
        ServletTime.append(int(info[-1]))
    print("JDBC Average Time: " + str(sum(JDBCTime)/len(JDBCTime)))
    print("Servlet Average Time: " + str(sum(ServletTime)/len(ServletTime)))
    w = open('results.txt', 'a')
    w.write("JDBC Average Time: " + str(sum(JDBCTime)/len(JDBCTime)) + "\n")
    w.write("Servlet Average Time: " + str(sum(ServletTime)/len(ServletTime))+ "\n")
    w.close()

main()
