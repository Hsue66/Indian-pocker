#include<iostream>
#include<cstdlib>
#include<ctime>

using namespace std;

int cardDeck[10];

int chips=20;	// �� Ĩ��
int betchip=0;	// ����Ĩ
int cchips=20;	// ��ǻ�� Ĩ��
int cbetchip=0;	// �� ����Ĩ

int temp=0;

int total=0;	// ���õ� Ĩ ��

	int card=0;		// playerī��
	int card1=0;	// ��ǻ��ī��

int distributeCard()	// ī�� �й��Լ�
{
	int wrong;
	int tcard;

	do
	{
		wrong=0;
		tcard=rand()%10;	// 0~9����

		// ���� ī�� ǥ���ϱ� (2���̻� �ߺ�����)
		for(int i=0; i<10; i++)
		{
			if(i==tcard)
			{
				if(cardDeck[i]==2)
					wrong++;
				else
					cardDeck[i]=cardDeck[i]+1;
			}
		}
	}while(wrong!=0);
	
	return tcard+1;
}
	
void betting()	// �������Լ�
{
	cout<<" �󸶸� �����Ͻðڽ��ϱ�? ";
	cin>>temp;
	betchip+=temp;
	chips=chips-temp;
	cout<<endl;
	cout<<" ����� "<<temp<<"Ĩ�� �����߽��ϴ�. ���� "<<betchip<<"��"<<endl;

		
	cbetchip+=2;
	cout<<" ��ǻ�Ͱ� 2Ĩ�� �����߽��ϴ�. ���� "<<cbetchip<<"��"<<endl;
	cchips=cchips-2;
		
	total=total+temp+2;
	cout<<" ���� "<<total<<"���� Ĩ�� ���õǾ��ֽ��ϴ�."<<endl<<endl;
}

void showChips()	// ���� �����ֱ�
{
	cout<<" ��Ĩ��: "<<chips<<endl;
	cout<<" ��ǻ��Ĩ��: "<<cchips<<endl;
}

int main()
{
	// ī�嵦
	for(int i=0; i<10; i++)
		cardDeck[i]=0;

	srand((unsigned int)time(NULL));	// �����ϰ�

	cout<<endl;
	cout<<" ===========�ε�� ��Ŀ============"<<endl;
	cout<<"         ������ �����մϴ�."<<endl;
	cout<<" =================================="<<endl<<endl;


	for(int i=0; i<10; i++)	 // ���ӽ���
	{
		cout<<" <"<<i+1<<"���带 �����մϴ�>"<<endl<<endl;
		cout<<" 1Ĩ�� �⺻�����մϴ�."<<endl<<endl;
		chips=chips-1;
		cchips=cchips-1;

		// ī��й�
		card = distributeCard();
		card1 = distributeCard();

		cout<<" *��ǻ���� ī��� "<<card1<<"�Դϴ�.*"<<endl;


		cout<<" *�� ī��"<<card<<endl<<endl;

		// �� ���� ����
		while(1)
		{
			char choice;

			cout<<" ������ �����Ͻðڽ��ϱ�?(y/n)";
			cin>>choice;
			cout<<endl;
			// ��������
			if(choice=='y' || choice=='Y' )
			{
				betting();
				cout<<" �� ���� Ĩ����: "<<chips<<endl;
				cout<<" ������õ� Ĩ ��: "<<total<<endl;
				if(chips<0)
				{
						cout<<" Ĩ�� ��� �����Ͽ����Ƿ� ��ǻ���� �¸��Դϴ�."<<endl;
					}
				if(cchips<0)
					{
						cout<<" Ĩ�� ��� �����Ͽ����Ƿ� ������� �¸��Դϴ�."<<endl;
					}
				// ���� �� ���õ� ���
				if(betchip==cbetchip)
				{
					cout<<endl;
					cout<<" ���� Ĩ���� ���õǾ����Ƿ� ������ �����մϴ�."<<endl;
					cout<<" ��ī�� :" <<card <<" ��ǻ��ī�� :" <<card1<<endl;
					if(card>card1)	// ������ �̱���
					{
						cout<<" ����� �¸� �Ͽ����ϴ�. Ĩ�� ����ϴ�."<<endl<<endl;
						chips=chips+total;

						betchip=0;
						cbetchip=0;
						total=0;
						showChips();
					}
					else if(card<card1)		// ��ǻ�Ͱ� �̱���
					{
						cout<<" ��ǻ�Ͱ� �¸� �Ͽ����ϴ�. ��ǻ�Ͱ� Ĩ�� ����ϴ�."<<endl<<endl;
						cchips=cchips+total;

						betchip=0;
						cbetchip=0;
						total=0;
						showChips();
					}
					else	// ������ ���
					{
						cout<<" �����̹Ƿ� Ĩ�� ������ä ������ �����մϴ�."<<endl;
						betchip=0;
						cbetchip=0;
						showChips();
					}
					break;
				}
			}
			// ��������
			else
			{
				cout<<" ������ �����ϼ̽��ϴ�. ��ǻ���� �¸��Դϴ�."<<endl;
				cchips=total+cchips;

				// 10ī���� ��� �г�Ƽ
				if(card==10)
				{
					cout<<" 10ī���̹Ƿ� �г�Ƽ�� ����˴ϴ�. Ĩ10���� �ҽ��ϴ�."<<endl;
					chips=chips-10;
					cchips=cchips+10;
					if(chips<0)
					{
						cout<<" Ĩ�� ��� �����Ͽ����Ƿ� ��ǻ���� �¸��Դϴ�."<<endl;
					}
				}
				showChips();
				betchip=0;
				cbetchip=0;
				total=0;
				break;
			}
		}		

		// ��� ������ ���
		if(chips<0 || cchips<0)
		{
			if(cchips==0)
				cout<<" ��ǻ�Ͱ� Ĩ�� ��� �����Ͽ� �¸��ϼ̽��ϴ�."<<endl;
			else
				cout<<" Ĩ�� ��� �����Ͽ����Ƿ� ��ǻ���� �¸��Դϴ�."<<endl;
			cout<<"=================================================="<<endl;
			break;
		}
		cout<<" ���� ���带 �����մϴ�."<<endl;
		cout<<"------------------------------------------------"<<endl;
	}
	return 0;
}